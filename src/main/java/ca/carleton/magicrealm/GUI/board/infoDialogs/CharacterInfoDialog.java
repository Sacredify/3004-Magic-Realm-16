package ca.carleton.magicrealm.GUI.board.infoDialogs;

import ca.carleton.magicrealm.GUI.board.BoardPanel;
import ca.carleton.magicrealm.GUI.board.BoardServices;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;

import javax.swing.*;

/**
 * Created by Tony on 23/02/2015.
 */
public class CharacterInfoDialog extends JDialog {
    private static final int CHARACTER_INFO_DIALOG_WIDTH = 760;
    private static final int CHARACTER_INFO_DIALOG_HEIGHT = 600;

    private static final String AMAZON_DETAIL_PATH = "image/characterdetail/amazon.jpg";
    private static final String BLACK_KNIGHT_DETAIL_PATH = "image/characterdetail/black_knight.jpg";
    private static final String CAPTAIN_DETAIL_PATH = "image/characterdetail/captain.jpg";
    private static final String DWARF_DETAIL_PATH = "image/characterdetail/dwarf.jpg";
    private static final String ELF_DETAIL_PATH = "image/characterdetail/elf.jpg";
    private static final String SWORDSMAN_DETAIL_PATH = "image/characterdetail/swordsman.jpg";
    private static final String BERSERKER_DETAIL_PATH = "image/characterdetail/berserker.jpg";
    private static final String WHITE_KNIGHT_PATH = "image/characterdetail/white_knight.jpg";

    BoardServices boardServices = new BoardServices();

    JLabel characterInfoLabel;

    public CharacterInfoDialog(final AbstractCharacter character) {
        this.setTitle(BoardPanel.CHARACTER_INFO_BUTTON_TEXT);

        this.setSize(CHARACTER_INFO_DIALOG_WIDTH, CHARACTER_INFO_DIALOG_HEIGHT);

        this.characterInfoLabel = new JLabel();
        this.characterInfoLabel.setSize(CHARACTER_INFO_DIALOG_WIDTH, CHARACTER_INFO_DIALOG_HEIGHT);
        this.characterInfoLabel.setLocation(0, 0);
        ImageIcon imageIcon = new ImageIcon();

        if (character.getEntityInformation() == EntityInformation.CHARACTER_AMAZON) {
            imageIcon = this.boardServices.createImageIcon(AMAZON_DETAIL_PATH);
        } else if (character.getEntityInformation() == EntityInformation.CHARACTER_BLACK_KNIGHT) {
            imageIcon = this.boardServices.createImageIcon(BLACK_KNIGHT_DETAIL_PATH);
        } else if (character.getEntityInformation() == EntityInformation.CHARACTER_CAPTAIN) {
            imageIcon = this.boardServices.createImageIcon(CAPTAIN_DETAIL_PATH);
        } else if (character.getEntityInformation() == EntityInformation.CHARACTER_DWARF) {
            imageIcon = this.boardServices.createImageIcon(DWARF_DETAIL_PATH);
        } else if (character.getEntityInformation() == EntityInformation.CHARACTER_ELF) {
            imageIcon = this.boardServices.createImageIcon(ELF_DETAIL_PATH);
        } else if (character.getEntityInformation() == EntityInformation.CHARACTER_SWORDSMAN) {
            imageIcon = this.boardServices.createImageIcon(SWORDSMAN_DETAIL_PATH);
        } else if (character.getEntityInformation() == EntityInformation.CHARACTER_BERSERKER) {
            imageIcon = this.boardServices.createImageIcon(BERSERKER_DETAIL_PATH);
        } else if (character.getEntityInformation() == EntityInformation.CHARACTER_WHITE_KNIGHT) {
            imageIcon = this.boardServices.createImageIcon(WHITE_KNIGHT_PATH);
        }
        this.characterInfoLabel.setIcon(imageIcon);
        this.add(this.characterInfoLabel);
        this.setVisible(true);
    }
}
