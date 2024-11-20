package champollion;

import lombok.Getter;

@Getter
public class UE {
    private final String intitule;
    private final int heuresCM;
    private final int heuresTD;
    private final int heuresTP;

    public UE(String intitule, int heuresCM, int heuresTD, int heuresTP) {
        this.intitule = intitule;
        this.heuresCM = heuresCM;
        this.heuresTD = heuresTD;
        this.heuresTP = heuresTP;
    }
}