package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class State {

    Bottle bottleA;
    Bottle bottleB;

}
