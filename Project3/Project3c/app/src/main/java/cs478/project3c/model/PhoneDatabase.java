package cs478.project3c.model;

import java.util.Arrays;
import java.util.List;

import cs478.project3c.R;

public class PhoneDatabase {

    public final static Phone IPHONE_11 =
            new Phone("Apple iPhone 11", R.drawable.iphone_11, "https://www.apple.com/iphone-11/");

    public final static Phone IPHONE_11_PRO =
            new Phone("Apple iPhone 11 Pro", R.drawable.iphone_11_pro, "https://www.apple.com/iphone-11-pro/");

    public final static Phone IPHONE_11_PRO_MAX =
            new Phone("Apple iPhone 11 Pro Max", R.drawable.iphone_11_pro_max, "https://www.apple.com/iphone-11-pro/");

    public final static Phone PIXEL_3 =
            new Phone("Google Pixel 3", R.drawable.pixel_3, "https://store.google.com/product/pixel_3");

    public final static Phone PIXEL_3_XL =
            new Phone("Google Pixel 3 XL", R.drawable.pixel_3_xl, "https://store.google.com/product/pixel_3");

    public final static Phone PIXEL_3A =
            new Phone("Google Pixel 3a", R.drawable.pixel_3a, "https://store.google.com/product/pixel_3");

    public final static Phone PIXEL_3A_XL =
            new Phone("Google Pixel 3a XL", R.drawable.pixel_3a_xl, "https://store.google.com/product/pixel_3");

    public final static Phone GALAXY_S10E =
            new Phone("Samsung Galaxy S10e", R.drawable.galaxy_s10e, "https://www.samsung.com/us/mobile/galaxy-s10/");

    public final static Phone GALAXY_S10 =
            new Phone("Samsung Galaxy S10", R.drawable.galaxy_s10, "https://www.samsung.com/us/mobile/galaxy-s10/");

    public final static Phone GALAXY_S10PLUS =
            new Phone("Samsung Galaxy S10+", R.drawable.galaxy_s10_plus, "https://www.samsung.com/us/mobile/galaxy-s10/");

    public final static List<Phone> ALL_PHONES = Arrays.asList(
            IPHONE_11, IPHONE_11_PRO, IPHONE_11_PRO_MAX,
            PIXEL_3, PIXEL_3_XL, PIXEL_3A, PIXEL_3A_XL,
            GALAXY_S10E, GALAXY_S10, GALAXY_S10PLUS
    );

    private PhoneDatabase() {}

}
