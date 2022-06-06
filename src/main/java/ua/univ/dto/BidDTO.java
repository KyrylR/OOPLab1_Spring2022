package ua.univ.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    private String workPurpose;
    private boolean isFinished;
    private String driverFeedback;
    private String driverId;
}
