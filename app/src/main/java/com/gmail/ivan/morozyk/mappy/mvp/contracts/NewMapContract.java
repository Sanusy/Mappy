package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface NewMapContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        @StateStrategyType(SkipStrategy.class)
        void showUsers(@NonNull Flowable<User> users);

        void openCreatedMap(@NonNull Map addedMap);

        void cancel();

        void emptyTitle();

        @StateStrategyType(SkipStrategy.class)
        void openNewUser(@NonNull List<User> addedUsers);
    }

    interface Presenter extends BaseContract.Presenter {

        void loadUsers();

        void createAndOpen(@NonNull Map map);

        void cancel();

        void addUser();

        void userAdded(@NonNull String userEmail);
    }

    interface Router extends BaseContract.Router {

        void openCreatedMap();

        void cancel();

        void openNewUser();
    }
}
