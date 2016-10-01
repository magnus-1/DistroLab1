package admintool.controler;

import admintool.model.WebShopModel;
import admintool.view.UserView;
import ui.UserInfo;

import java.util.Collection;

/**
 * Created by o_0 on 2016-10-01.
 */
public class AdminToolControler {
    private UserView view;
    private WebShopModel model;

    public AdminToolControler(UserView view, WebShopModel model) {
        this.view = view;
        this.model = model;
    }

    public Collection<UserInfo> getUsers() {
        return model.getUsers();
    }

    public void startApp() {
        view.setControlerDelegate(this);
        view.start();

    }
}
