"use strict";

define([
    "jquery",
    "bootstrap",
    "lodash"
], ($, bootstrap, _) =>{
    return {
        ajaxFill(config) {
            const ajax_url = _.get(config, "ajax_url", null);
            const dom_selector = $(_.get(config, "selector", null));
            const factory = _.get(config, "factory", null);
            if (_.isFunction(factory)) {
                if (_.isString(ajax_url)) {
                    $.get(ajax_url, (data) => {
                        factory(dom_selector, data);
                    })
                } else {
                    factory(dom_selector, null)
                }
            }
            return this;
        }
    }
});
