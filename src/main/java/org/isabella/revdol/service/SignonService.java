package org.isabella.revdol.service;


import org.isabella.revdol.domin.Signon;

import java.util.List;

public interface SignonService {
    List<Signon> getSignonList();
    Signon getSignon(String qq,String password);
    void insertSignon(Signon signon);
    void updateSignon(Signon signon);

}
