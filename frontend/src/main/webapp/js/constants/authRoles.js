'use strict';

app.constant('USER_ROLES', {
    supervisor: 'SUPERVISOR',
    admin: 'ADMINISTRATOR',
    order_manager: 'ORDER_MANAGER',
    processing_manager: 'PROCESSING_MANAGER',
    courier: 'COURIER',
    guest: 'GUEST'
});

app.constant('USER_LOCAL_ROLES', {
    supervisor: 'Супервизор',
    admin: 'Администратор',
    order_manager: 'Менеджер по приему заказов',
    processing_manager: 'Менеджер по обработке заказов',
    courier: 'Курьер',
    guest: 'Гость'
});