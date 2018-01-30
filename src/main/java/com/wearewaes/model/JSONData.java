package com.wearewaes.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class JSONData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Size(max = 5000)
    private String left;

    @Lob
    @Size(max = 5000)
    private String right;

    @NotNull
    private Long diffId;

    /**
     * Default constructor for a new JSONData.
     * This object represents a map for data comparision
     */
    public JSONData() {}

    /**
     * Constructor for a new JSONData only with the user specified ID.
     * This object represents a map for data comparision
     *
     * @param diffId - user specified ID
     */
    public JSONData(Long diffId) {
        this.diffId = diffId;
    }

    /**
     * Constructor for a new JSONData receiving all parameters.
     * This object represents a map for data comparision
     *
     * @param id - database ID
     * @param left - left JSON file base64 encoded
     * @param right - right JSON file base64 encoded
     * @param diffId - user specified ID
     */
    public JSONData(Long id, String left, String right, Long diffId) {
        this.id = id;
        this.left = left;
        this.right = right;
        this.diffId = diffId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public Long getDiffId() {
        return diffId;
    }

    public void setDiffId(Long diffId) {
        this.diffId = diffId;
    }

    @Transient
    public boolean hasLeftData() {
        return left != null;
    }

    @Transient
    public boolean hasRightData() {
        return right != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JSONData jsonData = (JSONData) o;

        return id != null ? id.equals(jsonData.id) : jsonData.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
