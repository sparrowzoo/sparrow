package com.sparrow.tracer;

public class TracerTest {
    public static void main(String[] args) {
        Tracer tracer = TracerBuilder.startTracer();
        Span root = tracer.build("l1").start();
        Span l2 = tracer.getSpanBuilder().asChild(tracer.root()).name("l2").start();
        l2.finish();
        Span l3 = tracer.getSpanBuilder().asChild(l2).name("l3").start();
            Span l31 = tracer.getSpanBuilder().asChild().name("l31").start();
                Span l32 = tracer.getSpanBuilder().asChild().name("l32").start();
                    Span l321 = tracer.getSpanBuilder().asChild().forwardParentCursor(false).name("l32-1").start();
                    l321.finish();
                l32.finish();
            l31.finish();
        l3.finish();
        Span l4 = tracer.getSpanBuilder().asChild().name("l4").start();
        l4.finish();
        tracer.root().finish();
        System.out.println(tracer.walking());
    }
}
