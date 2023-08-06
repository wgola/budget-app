package com.budget.application.response.provider.tags;

import java.util.List;

import com.budget.application.model.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsList {

    private List<Tag> tags;
}
