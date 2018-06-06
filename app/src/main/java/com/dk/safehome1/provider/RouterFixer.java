package com.dk.safehome1.provider;

import com.dk.safehome1.db.RouterViewModel;
import com.dk.safehome1.entity.Router;

import java.util.List;

/**
 * Created by dk on 6/6/18.
 */

public class RouterFixer {

    public static final int ROUTERS_IN_DATABASE = 4;

   private RouterViewModel mRouterViewModel;
    private List<Router> mRouterList;

    public RouterFixer() {
    }

    public RouterFixer(RouterViewModel routerViewModel, List<Router> routerList) {
        mRouterViewModel = routerViewModel;
        mRouterList = routerList;
    }

    public RouterViewModel getRouterViewModel() {
        return mRouterViewModel;
    }

    public void setRouterViewModel(RouterViewModel routerViewModel) {
        mRouterViewModel = routerViewModel;
    }

    public List<Router> getRouterList() {
        return mRouterList;
    }

    public void setRouterList(List<Router> routerList) {
        mRouterList = routerList;
    }

    public int fixInDatabase() {
        //routers added to database
        int count = 0;
        for (Router router : mRouterList) {
            if(ROUTERS_IN_DATABASE == count) break;
            mRouterViewModel.insert(router);
            count++;
        }

        return count;
    }
}
