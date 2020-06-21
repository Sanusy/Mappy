package com.gmail.ivan.morozyk.mappy.mvp.contracts;

import androidx.annotation.NonNull;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface AddUserContract {

    @StateStrategyType(AddToEndStrategy.class)
    interface View extends BaseContract.View {

        void invalidEmail();

        void alreadyAdded();

        void success(@NonNull String email);
    }

    interface Presenter extends BaseContract.Presenter {

        void addUser(@NonNull String email);
    }
}