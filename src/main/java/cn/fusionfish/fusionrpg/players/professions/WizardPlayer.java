package cn.fusionfish.fusionrpg.players.professions;

import cn.fusionfish.fusionrpg.players.FusionRPGPlayer;
import cn.fusionfish.fusionrpg.players.Profession;

public class WizardPlayer extends FusionRPGPlayer {

    private Element element = Element.FIRE;

    public WizardPlayer() {
        setProfession(Profession.Wizard);
    }

    public enum Element {

        FIRE("火"),
        ICE("冰"),
        THUNDER("雷"),
        WIND("风"),
        WATER("水"),
        WOOD("木"),
        EARTH("土");


        private final String name;
        Element(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
