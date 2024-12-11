package com.atlantis.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope("singleton")
@Scope("prototype")
public class Customer {
}
