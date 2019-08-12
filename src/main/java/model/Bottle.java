package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Bottle {

    int maxVolume;
    int currentVolume;

}
