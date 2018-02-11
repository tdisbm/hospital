requirejs.config({
    "baseUrl": "../js/frontend/app",
    "paths": {
        "field.util": "../../util/field_util",
        "medic.util": "../../util/medic_util",

        "jquery": "../../../vendor/jquery-3.2.0/jquery-3.2.0.min",
        "bootstrap": "../../../vendor/bootstrap-3.3.7/dist/js/bootstrap",
        "bootstrap.collapse": "../../../vendor/bootstrap-3.3.7/js/collapse",
        "bootstrap.transition": "../../../vendor/bootstrap-3.3.7/js/transition",
        "bootstrap.datetimepicker": "../../../vendor/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min",
        "moment": "../../../vendor/momentjs/moment",
        "lodash": "../../../vendor/lodash/lodash"
    },

    "shim": {
        "bootstrap" : {
            "deps": ["jquery"]
        },
        "bootstrap.collapse": {
            "deps": ["jquery"]
        },
        "bootstrap.transition": {
            "deps": ["jquery"]
        },
        "bootstrap.datetimepicker": {
            "deps": ["jquery", "bootstrap", "moment"]
        },
    }
});

requirejs([
    "jquery",
    "bootstrap",
    "bootstrap.collapse",
    "bootstrap.transition",
    "bootstrap.datetimepicker",
    "moment",
    "lodash",

    "main",

    "medic.util",
    "field.util"
]);
