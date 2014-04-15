'use strict';

app.factory("OrderREST", function($resource) {
        return $resource("/backend/api/orders/:id:rpath", {}, {
            readOne: {
                method: 'GET',
                params: {id: '@id'},
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            create: {
                method:'POST',
                params: {id: ''},
                headers: {
                'Content-Type': 'application/json'
                }
            },
            update: {
                method:'PUT',
                params: {id: '@id'},
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            delete: {
                method:'DELETE',
                params: {id: '@id'},
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        });
    });


app.factory("OrderStateREST", function($resource) {
        return $resource("/backend/api/orders/:id/state", {}, {
            updateState: {
            	method:'PUT',
                params: {id: '@id'},
                headers: {
                    'Content-Type': 'application/json'
                }
            }
       });
    });

   
app.factory("OrdersREST", function($resource) {
    return $resource("/backend/api/orders/p/:page", {}, {
        readAll: {
            method: "GET",
            params: {page: '@page'},
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});

app.factory("OrderState", function(ORDER_STATE, LOCAL_STATE) {
    return {
        possible: function (state) {
            var states = new Array();
            var ind = 0;
            if (state == ORDER_STATE.canceled || state == ORDER_STATE.closed)
                return states;
            if (state == ORDER_STATE.bad) {
                states[ind++] = {value: ORDER_STATE.new, display: LOCAL_STATE.new};
                return states;
            }
            if (state == ORDER_STATE.new)
                states[ind++] = {value : ORDER_STATE.accepted, display : LOCAL_STATE.accepted};
            if (state == ORDER_STATE.accepted)
                states[ind++] = {value : ORDER_STATE.processing, display : LOCAL_STATE.processing};
            if (state == ORDER_STATE.processing)
                states[ind++] = {value : ORDER_STATE.ready, display : LOCAL_STATE.ready};
            if (state == ORDER_STATE.ready)
                states[ind++] = {value : ORDER_STATE.delivery, display : LOCAL_STATE.delivery};
            if (state == ORDER_STATE.delivery)
                states[ind++] = {value : ORDER_STATE.closed, display : LOCAL_STATE.closed};

            states[ind++] = {value: ORDER_STATE.bad, display: LOCAL_STATE.bad};
            return states;
        },
        getlocal: function (state) {
            switch (state) {
                case ORDER_STATE.new:
                    return LOCAL_STATE.new; break;
                case ORDER_STATE.accepted:
                    return LOCAL_STATE.accepted; break;
                case ORDER_STATE.processing:
                    return LOCAL_STATE.processing; break;
                case ORDER_STATE.ready:
                    return LOCAL_STATE.ready; break;
                case ORDER_STATE.delivery:
                    return LOCAL_STATE.delivery; break;
                case ORDER_STATE.bad:
                    return LOCAL_STATE.bad; break;
                case ORDER_STATE.canceled:
                    return LOCAL_STATE.canceled; break;
                case ORDER_STATE.closed:
                    return LOCAL_STATE.closed; break;
            }
            return null;
        }
    };
});