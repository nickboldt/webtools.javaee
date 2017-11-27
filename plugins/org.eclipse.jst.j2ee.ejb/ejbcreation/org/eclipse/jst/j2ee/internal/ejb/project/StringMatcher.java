/***************************************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: IBM Corporation - initial API and implementation
 **************************************************************************************************/
package org.eclipse.jst.j2ee.internal.ejb.project;


import java.util.Vector;

public class StringMatcher {
	public static class Position {
		int start; //inclusive
		int end; //exclusive

		public Position(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}
	}

	protected String fPattern;
	protected int fLength; // pattern length
	protected boolean fIgnoreWildCards;
	protected boolean fIgnoreCase;
	protected boolean fHasLeadingStar;
	protected boolean fHasTrailingStar;
	protected String fSegments[]; //the given pattern is split into * separated segments
	/* boundary value beyond which we don't need to search in the text */
	protected int fBound = 0;
	protected static final char fSingleWildCard = '\u0000';

	/**
	 * StringMatcher constructor takes in a String object that is a simple pattern which may contain
	 * |221*|222 for 0 and many characters and |221?|222 for exactly one character. Also takes as
	 * parameter a boolean object specifying if case should be ignored
	 * 
	 * @deprecated Use StringMatcher(pattern, ignoreCase, ignoreWildCards).
	 */
	public StringMatcher(String aPattern, boolean ignoreCase) {
		this(aPattern, ignoreCase, false);
	}

	/**
	 * StringMatcher constructor takes in a String object that is a simple pattern which may contain
	 * |221*|222 for 0 and many characters and |221?|222 for exactly one character.
	 * 
	 * Literal '*' and '?' characters must be escaped in the pattern e.g., "\*" means literal "*",
	 * etc.
	 * 
	 * Escaping any other character (including the escape character itself), just results in that
	 * character in the pattern. e.g., "\a" means "a" and "\\" means "\"
	 * 
	 * If invoking the StringMatcher with string literals in Java, don't forget escape characters
	 * are represented by "\\".
	 * 
	 * @param aPattern
	 *            the pattern to match text against
	 * @param ignoreCase
	 *            if true, case is ignored
	 * @param ignoreWildCards
	 *            if true, wild cards and their escape sequences are ignored (everything is taken
	 *            literally).
	 */
	public StringMatcher(String aPattern, boolean ignoreCase, boolean ignoreWildCards) {
		fIgnoreCase = ignoreCase;
		fIgnoreWildCards = ignoreWildCards;
		fLength = aPattern.length();

		/* convert case */
		if (fIgnoreCase) {
			fPattern = aPattern.toUpperCase();
		} else {
			fPattern = aPattern;
		}

		if (fIgnoreWildCards) {
			parseNoWildCards();
		} else {
			parseWildCards();
		}
	}

	/**
	 * Find the first occurrence of the pattern between <code>start</code)(inclusive) 
	 * and <code>end</code>(exclusive).  
	 * @param <code>text</code>, the String object to search in 
	 * @param <code>start</code>, the starting index of the search range, inclusive
	 * @param <code>end</code>, the ending index of the search range, exclusive
	 * @return an <code>StringMatcher.Position</code> object that keeps the starting 
	 * (inclusive) and ending positions (exclusive) of the first occurrence of the 
	 * pattern in the specified range of the text; return null if not found or subtext
	 * is empty (start==end). A pair of zeros is returned if pattern is empty string
	 * Note that for pattern like "*abc*" with leading and trailing stars, position of "abc"
	 * is returned. For a pattern like"*??*" in text "abcdf", (1,3) is returned
	 */

	public StringMatcher.Position find(String text, int start, int end) {
		if (fPattern == null || text == null)
			throw new IllegalArgumentException();
		int localStart = start;
		int localEnd = end;
		
		int tlen = text.length();
		if (localStart < 0)
			localStart = 0;
		if (localEnd > tlen)
			localEnd = tlen;
		if (localEnd < 0 || localStart >= localEnd)
			return null;
		if (fLength == 0)
			return new Position(localStart, localStart);
		if (fIgnoreWildCards) {
			int x = posIn(text, localStart, localEnd);
			if (x < 0)
				return null;
			return new Position(x, x + fLength);
		}

		int segCount = fSegments.length;
		if (segCount == 0)//pattern contains only '*'(s)
			return new Position(localStart, localEnd);

		int curPos = localStart;
		int matchStart = -1;
		for (int i = 0; i < segCount && curPos < localEnd; ++i) {
			String current = fSegments[i];
			int nextMatch = regExpPosIn(text, curPos, localEnd, current);
			if (nextMatch < 0)
				return null;
			if (i == 0)
				matchStart = nextMatch;
			curPos = nextMatch + current.length();
		}
		return new Position(matchStart, curPos);
	}

	/**
	 * match the given <code>text</code> with the pattern
	 * 
	 * @return true if matched eitherwise false
	 * @param <code>text</code>, a String object
	 */
	public boolean match(String text) {
		return match(text, 0, text.length());
	}

	/**
	 * Given the starting (inclusive) and the ending (exclusive) poisitions in the <code>text</code>,
	 * determine if the given substring matches with aPattern
	 * 
	 * @return true if the specified portion of the text matches the pattern
	 * @param String
	 *            <code>text</code>, a String object that contains the substring to match
	 * @param int
	 *            <code>start<code> marks the starting position (inclusive) of the substring
	 * @param int <code>end<code> marks the ending index (exclusive) of the substring
	 */
	public boolean match(String text, int start, int end) {
		if (null == fPattern || null == text)
			throw new IllegalArgumentException();

		int localStart = start;
		int localEnd = end;
		
		if (localStart > localEnd)
			return false;

		if (fIgnoreWildCards)
			return fPattern.regionMatches(fIgnoreCase, 0, text, localStart, fLength);
		int segCount = fSegments.length;
		if (segCount == 0)//pattern contains only '*'(s) or empty pattern
			return true;
		if (localStart == localEnd)
			return fLength == 0;
		if (fLength == 0)
			return localStart == localEnd;

		int tlen = text.length();
		if (localStart < 0)
			localStart = 0;
		if (localEnd > tlen)
			localEnd = tlen;

		int tCurPos = localStart;
		int bound = localEnd - fBound;
		if (bound < 0)
			return false;
		int i = 0;
		String current = fSegments[i];
		int segLength = current.length();

		/* process first segment */
		if (!fHasLeadingStar) {
			if (!regExpRegionMatches(text, localStart, current, 0, segLength)) {
				return false;
			}
			++i;
			tCurPos = tCurPos + segLength;
		}

		/* process middle segments */
		for (; i < segCount && tCurPos <= bound; ++i) {
			current = fSegments[i];
			int currentMatch;
			int k = current.indexOf(fSingleWildCard);
			if (k < 0) {
				currentMatch = textPosIn(text, tCurPos, localEnd, current);
				if (currentMatch < 0)
					return false;
			} else {
				currentMatch = regExpPosIn(text, tCurPos, localEnd, current);
				if (currentMatch < 0)
					return false;
			}
			tCurPos = currentMatch + current.length();
		}

		/* process final segment */
		if (!fHasTrailingStar && tCurPos != localEnd) {
			int clen = current.length();
			return regExpRegionMatches(text, localEnd - clen, current, 0, clen);
		}
		return i == segCount;
	}

	/**
	 * This method parses the given pattern into segments seperated by wildcard '*' characters.
	 * Since wildcards are not being used in this case, the pattern consists of a single segment.
	 */
	private void parseNoWildCards() {
		fSegments = new String[1];
		fSegments[0] = fPattern;
		fBound = fLength;
	}

	/**
	 * This method parses the given pattern into segments seperated by wildcard '*' characters.
	 * 
	 * @param p,
	 *            a String object that is a simple regular expression with |221*|222 and/or
	 *            |221?|222
	 */
	private void parseWildCards() {
		if (fPattern.startsWith("*"))//$NON-NLS-1$
			fHasLeadingStar = true;
		if (fPattern.endsWith("*")) {//$NON-NLS-1$
			/* make sure it's not an escaped wildcard */
			if (fLength > 1 && fPattern.charAt(fLength - 2) != '\\') {
				fHasTrailingStar = true;
			}
		}

		Vector temp = new Vector();

		int pos = 0;
		StringBuffer buf = new StringBuffer();
		while (pos < fLength) {
			char c = fPattern.charAt(pos++);
			switch (c) {
				case '\\' :
					if (pos >= fLength) {
						buf.append(c);
					} else {
						char next = fPattern.charAt(pos++);
						/* if it's an escape sequence */
						if (next == '*' || next == '?' || next == '\\') {
							buf.append(next);
						} else {
							/* not an escape sequence, just insert literally */
							buf.append(c);
							buf.append(next);
						}
					}
					break;
				case '*' :
					if (buf.length() > 0) {
						/* new segment */
						temp.addElement(buf.toString());
						fBound += buf.length();
						buf.setLength(0);
					}
					break;
				case '?' :
					/* append special character representing single match wildcard */
					buf.append(fSingleWildCard);
					break;
				default :
					buf.append(c);
			}
		}

		/* add last buffer to segment list */
		if (buf.length() > 0) {
			temp.addElement(buf.toString());
			fBound += buf.length();
		}

		fSegments = new String[temp.size()];
		temp.copyInto(fSegments);
	}

	/**
	 * @param <code>text</code>, a string which contains no wildcard
	 * @param <code>start</code>, the starting index in the text for search, inclusive
	 * @param <code>end</code>, the stopping point of search, exclusive
	 * @return the starting index in the text of the pattern , or -1 if not found
	 */
	protected int posIn(String text, int start, int end) {//no wild card in pattern
		int max = end - fLength;

		if (!fIgnoreCase) {
			int i = text.indexOf(fPattern, start);
			if (i == -1 || i > max)
				return -1;
			return i;
		}

		for (int i = start; i <= max; ++i) {
			if (text.regionMatches(true, i, fPattern, 0, fLength))
				return i;
		}

		return -1;
	}

	/**
	 * @param <code>text</code>, a simple regular expression that may only contain '?'(s)
	 * @param <code>start</code>, the starting index in the text for search, inclusive
	 * @param <code>end</code>, the stopping point of search, exclusive
	 * @param <code>p</code>, a simple regular expression that may contains '?'
	 * @param <code>caseIgnored</code>, wether the pattern is not casesensitive
	 * @return the starting index in the text of the pattern , or -1 if not found
	 */
	protected int regExpPosIn(String text, int start, int end, String p) {
		int plen = p.length();

		int max = end - plen;
		for (int i = start; i <= max; ++i) {
			if (regExpRegionMatches(text, i, p, 0, plen))
				return i;
		}
		return -1;
	}

	/**
	 * 
	 * @return boolean
	 * @param <code>text</code>, a String to match
	 * @param <code>start</code>, int that indicates the starting index of match, inclusive
	 * @param <code>end</code> int that indicates the ending index of match, exclusive
	 * @param <code>p</code>, String, String, a simple regular expression that may contain '?'
	 * @param <code>ignoreCase</code>, boolean indicating whether code>p</code> is case sensitive
	 */
	protected boolean regExpRegionMatches(String text, int tStart, String p, int pStart, int plen) {
		int localTStart = tStart;
		int localPStart = pStart;
		int localPlen = plen;
		while (localPlen-- > 0) {
			char tchar = text.charAt(localTStart++);
			char pchar = p.charAt(localPStart++);

			/* process wild cards */
			if (!fIgnoreWildCards) {
				/* skip single wild cards */
				if (pchar == fSingleWildCard) {
					continue;
				}
			}
			if (pchar == tchar)
				continue;
			if (fIgnoreCase) {
				char tc = Character.toUpperCase(tchar);
				if (tc == pchar)
					continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * @param <code>text</code>, the string to match
	 * @param <code>start</code>, the starting index in the text for search, inclusive
	 * @param <code>end</code>, the stopping point of search, exclusive
	 * @param code>p
	 *            </code>, a string that has no wildcard
	 * @param <code>
	 *            ignoreCase</code>, boolean indicating wether code>p</code> is case sensitive
	 * @return the starting index in the text of the pattern , or -1 if not found
	 */
	protected int textPosIn(String text, int start, int end, String p) {

		int plen = p.length();
		int max = end - plen;

		if (!fIgnoreCase) {
			int i = text.indexOf(p, start);
			if (i == -1 || i > max)
				return -1;
			return i;
		}

		for (int i = 0; i <= max; ++i) {
			if (text.regionMatches(true, i, p, 0, plen))
				return i;
		}

		return -1;
	}
}