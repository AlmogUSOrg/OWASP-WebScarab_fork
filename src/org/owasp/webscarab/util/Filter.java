package org.owasp.webscarab.util;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

public abstract class Filter {
    EventListenerList listenerList = new EventListenerList();
    ChangeEvent changeEvent = null;

    /**
     * called by the ListFilter to determine if the object in the underlying list
     * should be included in the resulting list
     * @param object The object to be filtered, or not
     * @return true if the object should be filtered (i.e. not included) in the resulting list
     */    
    public abstract boolean filtered(Object object);
    
    /**
     *
     * @param l
     */    
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    /**
     *
     * @param l
     */    
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

    // 

    /**
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     * This makes sense
     * for dynamic filters that take a lot of effort to set up, but might be
     * inappropriate for simple filters.
     */    
    protected void fireFilterChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChangeListener.class) {
                // Lazily create the event:
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }

}

