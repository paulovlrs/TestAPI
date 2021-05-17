package com.paulo.pojo;

public class Pet {
    private Long id;
    private Integer categoryId;
    private String categoryName;
    private String name;
    private String photoUrls;
    private Integer tagsId;
    private String tagsName;
    private String status;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String getTagsName() {
        return tagsName;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTagsId() {
        return tagsId;
    }

    public void setTagsId(Integer tagsId) {
        this.tagsId = tagsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void SetAllValues() {
        setId(new Long(0));
        setCategoryId(0);
        setCategoryName("Grande");
        setName("Brutus");
        setPhotoUrls("https://dnl7t01l0fo05.cloudfront.net/fr/wp-content/uploads/sites/2/2018/04/brutus-bullmastiff.jpg");
        setTagsId(0);
        setTagsName("Cachorro");
        setStatus("available");
    }

    public String returnJsonBodyPet() {

        String body = "{\n" +
                "  \"id\": " + id.toString() + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": " + categoryId.toString() + ",\n" +
                "    \"name\": \"" + categoryName + "\"\n" +
                "  },\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"" + photoUrls + "\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": " + tagsId.toString() + ",\n" +
                "      \"name\": \"" + tagsName + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"" + status + "\"\n" +
                "}";
        return body;
    }
}
