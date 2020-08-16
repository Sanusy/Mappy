package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import com.gmail.ivan.morozyk.mappy.data.entity.Map;
import com.gmail.ivan.morozyk.mappy.data.entity.User;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapDetailsContract {

    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends BaseContract.View {

        @StateStrategyType(SkipStrategy.class)
        void showUsers(@NonNull Flowable<User> users);

        void showDetails(@NonNull Map map);

        @StateStrategyType(SkipStrategy.class)
        void openNewUser(@NonNull List<User> addedUsers);

        @StateStrategyType(SkipStrategy.class)
        void closeMap();
    }

    interface Presenter extends BaseContract.Presenter {

        void loadDetails();

        void showAddUser();

        void userAdded(@NonNull String userEmail);

        void leave();
    }
}
