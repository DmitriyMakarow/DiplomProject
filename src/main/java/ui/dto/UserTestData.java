package ui.dto;

import ui.enumUI.RadioLabel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserTestData {

    private String firstName;
    private String lastName;
    private String age;
    private String money;
    private RadioLabel gender;
    private String description;
}
