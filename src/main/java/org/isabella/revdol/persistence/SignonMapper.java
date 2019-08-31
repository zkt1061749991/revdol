package org.isabella.revdol.persistence;

import org.isabella.revdol.domin.Signon;

import java.util.List;

public interface SignonMapper {
    List<Signon> getSignonList();

    Signon getSignon(Signon signon);

    void insertSignon(Signon signon);
    void updateSignon(Signon signon);

}
