[![Donate](https://img.shields.io/badge/Donate-PayPal-orange.svg)](https://www.paypal.com/donate/?cmd=_donations&business=8UK2BZP2K8NSS)

# Assignables
A simple lightweight Java library to assign variables.

## Sample usage:

    Assignable a = new Assignable();
    Assignables.assign(5).into(a).parallelProcessing().timeout(5000).end().withAssignmentPolicy(new MD5AssignmentPolicy());
    System.out.println(a);
    
The output of this program will be the Hash of 5 using MD5 as you'd expect. The assignment will be performed on a separate Thread with a timeout of 5 seconds.

This library is a joke but if you find an actual use for it, good for you!

Browse javadoc here: https://aurasphere.github.io/assignables/
