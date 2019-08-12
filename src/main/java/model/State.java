package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class State {

    private Bottle bottleA;
    private Bottle bottleB;

}
