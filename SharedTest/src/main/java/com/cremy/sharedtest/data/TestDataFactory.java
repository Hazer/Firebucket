package com.cremy.sharedtest.data;

/**
 * This class allows to provide some fake data for the tests
 * Created by remychantenay on 22/05/2016.
 */
public final class TestDataFactory {


/*    *//**
     * Allows to make only one _FAKE_ recent item
     * @return
     *//*
    private static RecentItem makeRecentItem() {
        RecentItem recentItem = new RecentItem();
        recentItem.setHeading(new RecentItemHeading("Title", "Subtitle"));
        recentItem.setImages(new Images("https://lh5.googleusercontent.com/-0-IAIeqDRbQ/AAAAAAAAAAI/AAAAAAAAGok/I8yk2T8Oq8A/s0-c-k-no-ns/photo.jpg"));

        return recentItem;
    }*/


/*    *//**
     * Allows to make a _FAKE_ recents list
     * @param _count
     * @return
     *//*
    public static Recents makeRecentItems(int _count) {
        List<RecentItem> items = new ArrayList<RecentItem>();
        for (int i = 0; i < _count; i++) {
            items.add(makeRecentItem());
        }

        Recents recents = new Recents();
        recents.setEntries(items);

        return recents;
    }*/
}