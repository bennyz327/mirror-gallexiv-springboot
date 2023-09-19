package com.team.gallexiv.data.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class LinkMappingService {

    @Autowired
    private LinkMappingDao linkD;

    @Autowired
    private UserService userD;

    public List<LinkMapping> getLinksByUserId(Userinfo user) {
        return linkD.findByUserinfoByUserId(user);
    }

    public boolean updateUserLinks(String account, Map<String, String> links) {
        Userinfo user = userD.getUserByAccount(account);
        Collection<LinkMapping> oldLinks = getLinksByUserId(user);
        for (LinkMapping link : oldLinks) {
            System.out.println("即將刪除："+link.getLinkSite());
            linkD.delete(link);
        }
        for (Map.Entry<String, String> link : links.entrySet()) {
            LinkMapping newLink = new LinkMapping();
            newLink.setLinkSite(link.getKey());
            newLink.setLinkSource(link.getValue());
            newLink.setUserinfoByUserId(user);
            System.out.println("即將新增："+newLink.getLinkSite());
            linkD.save(newLink);
        }
        return true;
    }
}
