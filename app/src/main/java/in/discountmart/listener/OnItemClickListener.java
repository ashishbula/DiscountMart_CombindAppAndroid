package in.discountmart.listener;

import android.view.View;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListView;

/**
 * Created by The Rock on 3/16/2018.
 */

public interface OnItemClickListener {
    /**
     * Method called when an item has been clicked
     *
     * @param parent The MultiLevelListView containing the clicked view
     * @param view The view that was clicked (the view provided by the LevelWiseDirectAdapter)
     * @param item Object that was clicked
     * @param itemInfo ItemInfo object with information about the clicked object and its location
     *                 on the list
     */
    void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo);

    /**
     * Method called when a group item has been clicked
     *
     * @param parent The MultiLevelListView containing the cliked view
     * @param view The view that was clicked (the view provided by the LevelWiseDirectAdapter)
     * @param item Object that was clicked
     * @param itemInfo ItemInfo object with information about the clicked object and its location
     *                 on the list
     */
    void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo);
}
