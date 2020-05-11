package com.gmail.ivan.morozyk.mappy.mvp.contracts;

public interface MapDetailsContract {

    interface View extends BaseContract.View {

        void showDetails();

        void enableEdit(boolean enabled);

        void swipeSaveEdit();
    }

    interface Presenter extends BaseContract.Presenter {

        void loadDetails(int mapId);

        void showAddUser();

        void leave();

        void checkOwner();
    }

    interface Router extends BaseContract.Router {

        void openAddUser();

        void openLeaveConfirm();
    }
}
