package com.amazon.test.web.page;

import com.amazon.test.Selenium;
import okhttp3.HttpUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representation of a web page.
 * This class is responsible for building the url for the target page and make the target page currently active.
 * It also provides various operations which can be performed on currently active page.
 */
public class Page {

    /**
     * Constructs url and corresponding {@link Page} from given page configuration.
     * Note that this method only constructs {@link Page} but does not make it active.
     * A call to {@link Page#visit()} on the constructed {@link Page} is required to make it active.
     *
     * @param baseUrl         base URL of the page
     * @param pageType        type of the page to create. Used to append path segment of target page to baseUrl
     * @param queryParameters query parameters to append to the page URL
     * @return {@link Page} object with URL generated and ready to visit
     * @throws MalformedURLException If baseUrl is invalid
     */
    public static Page make(String baseUrl, PageType pageType, HashMap<String, String> queryParameters) throws MalformedURLException {
        HttpUrl url = HttpUrl.parse(baseUrl);
        if (url == null) throw new MalformedURLException("Malformed url : " + baseUrl);
        HttpUrl.Builder urlBuilder = url.newBuilder();
        if (!pageType.getPathSegment().isEmpty()) {
            urlBuilder.addEncodedPathSegments(pageType.getPathSegment());
        }
        if (queryParameters != null) {
            for (HashMap.Entry<String, String> param : queryParameters.entrySet()) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        return new Page(urlBuilder.build().toString());
    }

    /**
     * Constructs the {@link Page} from the currently visited URL and also set its the source code and content.
     *
     * @return currently visited {@link Page} object
     */
    public static Page currentPage() {
        Page page = new Page(currentUrl());
        setPageContent(page);
        return page;
    }

    public static String currentUrl() {
        return Selenium.browser().getCurrentUrl();
    }

    /**
     * Sets the body content of current webpage in the given {@link Page} content.
     *
     * @param page {@link Page} whose content to set
     */
    private static void setPageContent(Page page) {
        try {
            WebElement bodyElement = Selenium.browser().findElement(By.tagName("body"));
            page.pageContent = bodyElement.getText();
        } catch (NoSuchElementException exception) {
            page.pageContent = "";
        }
    }

    private final String url;
    private String pageContent;

    private Page(String url) {
        this.url = url;
        this.pageContent = "";
    }

    public String getUrl() {
        return this.url;
    }

    /**
     * Visits the url set in the {@link Page} to make it active and sets the page content.
     */
    public void visit() {
        Selenium.browser().get(this.url);
        setPageContent(this);
    }

    /**
     * Determines whether page content is empty.
     *
     * @return true if empty otherwise false
     */
    public boolean isContentEmpty() {
        return this.pageContent.isEmpty();
    }

    /**
     * Finds the content matching a pattern match group in the page content.
     *
     * @param regex      Pattern
     * @param matchGroup Match group to return
     * @return page content matched in the matchGroup of regex or null if no content matched
     */
    public String findContent(Pattern regex, int matchGroup) {
        Matcher matcher = regex.matcher(this.pageContent);
        boolean found = matcher.find();
        if (found) {
            return matcher.group(matchGroup);
        }
        return null;
    }

    /**
     * Determines whether page content contains given text ignoring case sensitivity.
     *
     * @param text text to find
     * @return true if found otherwise false
     */
    public boolean contentContainsIgnoreCase(String text) {
        return this.pageContent.toLowerCase().contains(text.toLowerCase());
    }

    /**
     * Determines whether page content contains given text.
     *
     * @param text text to find
     * @return true if found otherwise false
     */
    public boolean contentContains(String text) {
        return this.pageContent.contains(text);
    }

    /**
     * Get the value of given query parameter key in the URL of the page.
     *
     * @param parameterKey query parameter key whose value to get
     * @return value of query parameter or null if query parameter key is not found in the URL
     */
    public String getQueryParameter(String parameterKey) {
        HttpUrl currentUrl = HttpUrl.parse(url);
        if (currentUrl != null) {
            return currentUrl.queryParameter(parameterKey);
        }
        return null;
    }

    /**
     * Get the path segment of the URL.
     *
     * @return path segment
     */
    public String getPathSegment() {
        HttpUrl currentUrl = HttpUrl.parse(url);
        if (currentUrl != null) {
            return currentUrl.encodedPath();
        }
        return null;
    }

    /**
     * Returns a base URL consisting of scheme and host.
     *
     * @return base URL string
     */
    public String getBaseUrl() {
        HttpUrl currentUrl = HttpUrl.parse(url);
        if (currentUrl != null) {
            return currentUrl.scheme() + "://" + currentUrl.host();
        }
        return null;
    }
}
