'use strict';

app.constant('ORDER_STATE', {
    new: 'NEW',
    accepted: 'ACCEPTED',
    processing: 'IN_PROCESSING',
    ready: 'READY_FOR_DELIVERY',
    delivery: 'DELIVERY',
    bad: 'CAN_NOT_BE_EXECUTED',
    canceled: 'CANCELED',
    closed: 'CLOSED'
});

app.constant('LOCAL_STATE', {
    new: 'Новый',
    accepted: 'Принят',
    processing: 'В обработке',
    ready: 'Готов к доставке',
    delivery: 'Доставка',
    bad: 'Не может быть выполнен',
    canceled: 'Отменен',
    closed: 'Закрыт'
});