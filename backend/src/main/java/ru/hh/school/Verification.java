package ru.hh.school;

import static ru.hh.school.Params.*;
import static ru.hh.school.Params.MINIMUM_PAGE;

public class Verification {
    public static void checkPagingParams(int per_page, int page) {
        if (per_page > MAXIMUM_PER_PAGE)
            throw new IllegalArgumentException("The number of per_page is more than " + MAXIMUM_PER_PAGE);
        if (per_page < MINIMUM_PER_PAGE)
            throw new IllegalArgumentException("The number of per_page is less than " + MINIMUM_PAGE);
        if (page < MINIMUM_PAGE) throw new IllegalArgumentException("The number of page is less than " + MINIMUM_PAGE);
    }
}
