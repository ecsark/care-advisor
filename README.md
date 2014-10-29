care-advisor
============

Server end for Care

POST        /login               {username, password} -> {token}
POST        /signup              {username, password} -> ok
POST        /ask                 {token, symptom[]} -> {inquiry{q,r}}
