requirejs.config({
    "baseUrl": "../../js/admin/app",
    "paths": {
        "jquery": "../../../vendor/jquery-3.2.0/jquery-3.2.0.min",
        "bootstrap": "../../../vendor/bootstrap-3.3.7/dist/js/bootstrap",
        "ag-grid": "../../../vendor/ag-grid/ag-grid16.0.min",

        "lodash": "../../../vendor/lodash/lodash",
        "medic.util": "../../util/medic_util"
    },

    "shim": {
        "bootstrap" : {
            "deps": ["jquery"]
        },
    }
});

requirejs([
    "jquery",
    "bootstrap",
    "lodash",
    "ag-grid",

    "main"
]);
