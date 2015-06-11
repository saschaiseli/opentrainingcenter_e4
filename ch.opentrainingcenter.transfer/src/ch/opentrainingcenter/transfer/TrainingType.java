package ch.opentrainingcenter.transfer;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("nls")
public enum TrainingType {
    NONE(0, "unbekannt", "unbekannt", "icons/man_u_32_32.png"), //
    EXT_INTERVALL(1, "Extensives Intervall", "Extensives Intervall", "icons/man_ei_32_32.png"), //
    INT_INTERVALL(2, "Intensives Intervall", "Intensives Intervall", "icons/man_ii_32_32.png"), //
    LONG_JOG(3, "Long Jog", "Long Jog", "icons/man_lj_32_32.png"), //
    POWER_LONG_JOG(4, "Power Long Jog", "Power Long Jog", "icons/man_pj_32_32.png"), //
    TEMPO_JOG(5, "Tempo Dauerlauf", "Tempo Dauerlauf", "icons/man_tj_32_32.png");

    private final int index;
    private final String name;
    private final String message;
    private final String image;

    private TrainingType(final int index, final String name, final String message, final String image) {
        this.index = index;
        this.name = name;
        this.message = message;
        this.image = image;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getImage() {
        return image;
    }

    public static TrainingType getByIndex(final int index) {
        switch (index) {
        case 0:
            return NONE;
        case 1:
            return EXT_INTERVALL;
        case 2:
            return INT_INTERVALL;
        case 3:
            return LONG_JOG;
        case 4:
            return POWER_LONG_JOG;
        case 5:
            return TEMPO_JOG;
        default:
            throw new IllegalArgumentException(String.format("TrainingTyp mit dem Index %s existiert nicht", index)); //$NON-NLS-1$
        }
    }

    public static String[] getAllTypes() {
        final List<String> typeTitles = new ArrayList<String>();
        for (final TrainingType type : TrainingType.values()) {
            typeTitles.add(type.getName());
        }
        return typeTitles.toArray(new String[0]);
    }
}
