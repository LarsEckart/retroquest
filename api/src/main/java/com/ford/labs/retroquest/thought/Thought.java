/*
 * Copyright © 2020 Ford Motor Company
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ford.labs.retroquest.thought;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ford.labs.retroquest.columntitle.ColumnTitle;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Builder
public class Thought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private int hearts;
    private String topic;
    private boolean discussed;
    private String teamId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(
                    name = "topic",
                    referencedColumnName = "topic",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "teamId",
                    referencedColumnName = "teamId",
                    insertable = false,
                    updatable = false
            )
    })
    private ColumnTitle columnTitle;
    private Long boardId;

    public Thought(Long id, String message, int hearts, String topic, boolean discussed, String teamId, ColumnTitle columnTitle, Long boardId) {
        this.id = id;
        this.message = message;
        this.hearts = hearts;
        this.topic = topic;
        this.discussed = discussed;
        this.teamId = teamId;
        this.columnTitle = columnTitle;
        this.boardId = boardId;
    }

    private String getDiscussedString() {
        return discussed ? "yes" : "no";
    }

    @JsonIgnore
    public List<String> getCSVFields() {
        return Arrays.asList(columnTitle.getTitle(), message, String.valueOf(hearts), getDiscussedString());
    }

    public void toggleDiscussed() {
        discussed = !discussed;
    }

    public void incrementHearts() {
        hearts++;
    }

}
