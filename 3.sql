call addtoCart (3,1,1); -- görs en beställning
call addtoCart (3,1,5); -- lägger till en sko i samma best.
call addtoCart (3,1,5); -- antal2 på sko nr5

select * from ViewCustomerOrders; -- view för ordrarna, för å hålla reda på sko beställningarna