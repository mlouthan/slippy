package videotext.textanimation;

import java.util.ArrayList;
import java.util.List;

public class PagedText {

    private final String originalText;
    private final List<String> pages;

    public PagedText(final String text) {
        originalText = text;
        pages = paginateText(text);
    }

    public char getChar(final int page, final int position) {
        return pages.get(page).charAt(position);
    }

    public String substring(final int page, final int startPosition, final int endPosition) {
        return pages.get(page).substring(startPosition, endPosition);
    }

    public int length(final int page) {
        return pages.get(page).length();
    }

    public int pageCount() {
        return pages.size();
    }

    private List<String> paginateText(final String text) {
        List<String> paginatedText = new ArrayList<>();
        int lineLength = 20;
        int linesPerPage = 3;
        int lineCount = 1;
        int lineStart = 0;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length();) {
            int nextWhitespace = findMinPositiveValue(findNextWhitespace(text, i), text.length());
            int nextNewline = text.indexOf("\n", i);
            boolean addedNewline = false;
            String line = "";
            if (nextWhitespace - i > lineLength) {
                line = text.substring(lineStart, lineStart + lineLength - 1) + "-\n";
                lineStart = lineStart + lineLength;
                i = lineStart;
                addedNewline = true;
            } else if (nextNewline > 0 && nextNewline <= nextWhitespace) {
                line = text.substring(lineStart, nextNewline + 1);
                lineStart = nextNewline + 1;
                i = lineStart;
                addedNewline = true;
            } else if (nextWhitespace - lineStart > lineLength) {
                line = text.substring(lineStart, i - 1) + "\n";
                lineStart = i;
                i++;
                addedNewline = true;
            } else {
                i = Math.max(nextWhitespace, i+1);
            }

            if (addedNewline) {
                sb.append(line);
                if (lineCount + 1 > linesPerPage) {
                    paginatedText.add(sb.toString());
                    lineCount = 1;
                    sb = new StringBuilder();
                } else {
                    lineCount++;
                }
            }
        }
        sb.append(text.substring(lineStart, text.length()));
        paginatedText.add(sb.toString());
        return paginatedText;
    }

    private int findMinPositiveValue(final int one, final int two) {
        if (one < 0 && two < 0) {
            throw new RuntimeException("Can't find min of two negative values");
        } else if (one < 0) {
            return two;
        } else if (two < 0) {
            return one;
        } else {
            return Math.min(one, two);
        }
    }

    public int findNextWhitespace(final String text, final int startIndex) {
        for (int i = startIndex; i < text.length(); i++) {
            if (Character.isWhitespace(text.charAt(i))) {
                return i;
            }
        }
        return -1;
    }
}
