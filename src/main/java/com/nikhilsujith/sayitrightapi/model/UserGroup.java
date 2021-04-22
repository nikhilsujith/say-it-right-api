package com.nikhilsujith.sayitrightapi.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;

@Data
@NoArgsConstructor
@Getter
@Setter
@TypeAlias("UserGroup")
public class UserGroup {
	public String id;
    public String groupName;
    public String groupDesc;
    public String groupImage;
}
