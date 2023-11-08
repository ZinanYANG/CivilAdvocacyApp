package com.example.civiladvocacyapp;

import java.io.Serializable;
import java.util.List;

public class Official implements Serializable {
    private String name;
    private String officeTitle;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String party;
    private String phoneNumber;
    private String websiteUrl;
    private String email;
    private String photoUrl;
    private List<SocialChannel> channels;
    public Official(String name, String officeTitle, String address,
                    String city, String state, String zip, String party,
                    String phoneNumber, String websiteUrl, String email,
                    String photoUrl, List<SocialChannel> channels) {
        this.name = name;
        this.officeTitle = officeTitle;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.party = party;
        this.phoneNumber = phoneNumber;
        this.websiteUrl = websiteUrl;
        this.email = email;
        this.photoUrl = photoUrl;
        this.channels = channels;
    }

    public String getName() {
        return name;
    }

    public String getOfficeTitle() {
        return officeTitle;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getParty() {
        return party;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public List<SocialChannel> getChannels() {
        return channels;
    }

    public static class SocialChannel implements Serializable{
        private String type;
        private String id;
        public SocialChannel(String type, String id) {
            this.type = type;
            this.id = id;
        }
        public String getType() {
            return type;
        }
        public String getId() {
            return id;
        }
    }
}

