package com.nepal.naxa.smartnaari.mycircle.location;

/**
 * Created by nishon on 10/21/17.
 */

public interface LocationChangeListener {
    void locationChanged(String msg);

    void accurateLocationReceived(String msg);
}
