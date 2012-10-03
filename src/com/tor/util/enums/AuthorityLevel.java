package com.tor.util.enums;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
* Created by IntelliJ IDEA.
* User: tor
* Date: 27.08.12
* Time: 16:27
* $Rev::               $:  Revision of last commit
* $Author::            $:  Author of last commit
* $Date::              $:  Date of last commit
* To change this template use File | Settings | File Templates.
*/
public enum AuthorityLevel {

 /** make decision as the manager */
 TELL,

 /** convince people about decision */
 SELL,

 /** get input from team before decision */
 CONSULT,

 /** make decision together with team */
 AGREE,

 /** influence decision made by the team */
 ADVISE,

 /** ask feedback after decision by team */
 INQUIRE,

 /** no influence, let team work it out */
 DELEGATE;
    public int numberOfPoints() {
     return ordinal() + 1;
    }

    // It's ok to use the internal ordinal integer for the implementation
    public boolean isControlOriented() {
     return ordinal() < AGREE.ordinal();
    }

    // EnumSet is a Set implementation that benefits from the integer-like
    // nature of the enums
    public static Set DELEGATION_LEVELS = EnumSet.range(ADVISE, DELEGATE);

    // enums are comparable hence the usual benefits
    public static AuthorityLevel highest(List <AuthorityLevel>levels) {
     return Collections.max(levels);
    }
   }

