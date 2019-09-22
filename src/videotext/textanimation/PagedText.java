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
        for (int i = 0; i < text.length(); i++) {
            int nextWhitespace = findNextWhitespace(text, i);
            if (nextWhitespace - lineStart > lineLength) {
                sb.append(text.substring(lineStart, i - 1));
                lineStart = i;
                if (lineCount + 1 > linesPerPage) {
                    paginatedText.add(sb.toString());
                    lineCount = 1;
                    sb = new StringBuilder();
                } else {
                    lineCount++;
                    sb.append("\n");
                }
            }
        }
        sb.append(text.substring(lineStart, text.length()));
        paginatedText.add(sb.toString());
        return paginatedText;
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
