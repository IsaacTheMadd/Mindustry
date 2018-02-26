package io.anuke.mindustry.ui.dialogs;

import io.anuke.ucore.scene.ui.ScrollPane;
import io.anuke.ucore.scene.ui.layout.Table;

import static io.anuke.mindustry.Vars.netServer;
import static io.anuke.mindustry.Vars.ui;

public class AdminsDialog extends FloatingDialog {

    public AdminsDialog(){
        super("$text.server.admins");

        addCloseButton();

        setup();
        shown(this::setup);
    }

    private void setup(){
        content().clear();

        float w = 400f, h = 80f;

        Table table = new Table();

        ScrollPane pane = new ScrollPane(table, "clear");
        pane.setFadeScrollBars(false);

        if(netServer.admins.getAdmins().size == 0){
            table.add("$text.server.admins.none");
        }

        for(String ip : netServer.admins.getAdmins()){
            Table res = new Table("button");
            res.margin(14f);

            res.labelWrap("[LIGHT_GRAY]" + netServer.admins.getLastName(ip)).width(w - h - 24f);
            res.add().growX();
            res.addImageButton("icon-cancel", 14*3, () -> {
                ui.showConfirm("$text.confirm", "$text.confirmunadmin", () -> {
                    netServer.admins.unAdminPlayer(ip);
                    setup();
                });
            }).size(h).pad(-14f);

            table.add(res).width(w).height(h);
            table.row();
        }

        content().add(pane);
    }
}