package champollion;

import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UE ue = (UE) o;
        return heuresCM == ue.heuresCM &&
                heuresTD == ue.heuresTD &&
                heuresTP == ue.heuresTP &&
                Objects.equals(intitule, ue.intitule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intitule, heuresCM, heuresTD, heuresTP);
    }
}