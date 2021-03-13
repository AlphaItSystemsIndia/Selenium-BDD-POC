package com.amazon.test.web.page;

import org.openqa.selenium.InvalidArgumentException;

/**
 * Enum that represent the type of page.
 * Each page in the website have some name and a unique path segment appended to the base url.
 * This enum defines all page types and their corresponding page name and path segments.
 * The information in this enum is used by {@link Page} class to construct the target page URL.
 */
public enum PageType {
    Home(PageType.PAGE_HOME),
    SignIn(PageType.PAGE_SIGN_IN, "ap/signin");

    // All page names
    private static final String PAGE_HOME = "Home";
    private static final String PAGE_SIGN_IN = "Sign in";

    // name of the page
    private final String name;
    // unique path segment of page (for url generation)
    private String pathSegment;

    PageType(String name) {
        this(name, "");
    }

    PageType(String name, String pathSegment) {
        this.name = name;
        this.pathSegment = pathSegment;
    }

    public String getName() {
        return this.name;
    }

    public String getPathSegment() {
        return this.pathSegment;
    }

    public void setPathSegment(String pathSegment) {
        this.pathSegment = pathSegment;
    }

    /**
     * Creates the {@link PageType} enum from page name.
     * Page name passed must be known by the enum i.e. following values are allowed -
     * <p>
     * {@link #PAGE_HOME}
     * <p>
     * {@link #PAGE_SIGN_IN}
     *
     * @param pageName Name of known pages
     * @return {@link PageType} enum for the given pageName
     * @throws InvalidArgumentException if pageName is unknown
     */
    public static PageType makeFrom(String pageName) throws InvalidArgumentException {
        return makeFrom(pageName, null);
    }

    /**
     * Creates the {@link PageType} enum from page name and set its path segment.
     * Page name passed must be known by the enum i.e. following values are allowed -
     * <p>
     * {@link #PAGE_HOME}
     * <p>
     * {@link #PAGE_SIGN_IN}
     *
     * @param pageName    Name of known pages
     * @param pathSegment Path segment to set for the {@link PageType} enum
     * @return {@link PageType} enum for the given pageName with pathSegment set
     * @throws InvalidArgumentException if pageName is unknown
     */
    public static PageType makeFrom(String pageName, String pathSegment) throws InvalidArgumentException {
        PageType pageType;
        switch (pageName) {
            case PAGE_HOME:
                pageType = Home;
                break;
            case PAGE_SIGN_IN:
                pageType = SignIn;
                break;
            default:
                throw new InvalidArgumentException("Invalid page name : " + pageName);
        }
        if (pathSegment != null)
            pageType.setPathSegment(pathSegment);
        return pageType;
    }
}
