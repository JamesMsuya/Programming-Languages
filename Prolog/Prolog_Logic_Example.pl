
session(a,10,101).  
session(b,12,104). 
session(c,11,102). 
session(d,16,103). 
session(e,17,103). 
session(f,10,103).
session(g,15,104). 
session(h,13,104).

attendee(1,a).
attendee(1,b).
attendee(2,a).
attendee(3,b).
attendee(4,c).
attendee(5,d).
attendee(6,d).
attendee(6,a).
attendee(7,b).
attendee(7,c).
attendee(7,e).
attendee(8,c).
attendee(8,b).
attendee(9,e).
attendee(9,f).
attendee(10,f).
attendee(10,c).
attendee(11,b).
attendee(11,c).
attendee(11,f).
attendee(12,b).
attendee(12,g).


when(X,Y):-session(X,Y,_Z).
where(X,Y):-session(X,_Z,Y).
enrollment(X,Y):-attendee(X,Y).
schedule(S,P,T):-attendee(S,_Z),session(_Z,T,P).
usage(P,T):-session(_X,T,P).
conflict(X,Y):-session(X,T,R),session(Y,T1,R1), ((R =:= R1) , (abs(T-T1) < 2)),\+(X==Y).
meet(X,Y):-schedule(X,P,T),schedule(Y,P,T),\+(X==Y).