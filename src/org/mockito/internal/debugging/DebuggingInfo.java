package org.mockito.internal.debugging;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.mockito.internal.invocation.Invocation;
import org.mockito.internal.invocation.InvocationMatcher;
import org.mockito.internal.util.MockitoLogger;

public class DebuggingInfo {

    private final List<Invocation> stubbedInvocations = new LinkedList<Invocation>();
    private final List<InvocationMatcher> unstubbedInvocations = new LinkedList<InvocationMatcher>();

    public void addStubbedInvocation(Invocation invocation) {
        //TODO test 
        //this is required because we don't know if unstubbedInvocation was really stubbed later...
        Iterator<InvocationMatcher> unstubbedIterator = unstubbedInvocations.iterator();
        while(unstubbedIterator.hasNext()) {
            InvocationMatcher unstubbed = unstubbedIterator.next();
            if (unstubbed.getInvocation().equals(invocation)) {
                unstubbedIterator.remove();
            }
        }
        unstubbedInvocations.remove(invocation);
        stubbedInvocations.add(invocation);
    }

    public void addPotentiallyUnstubbed(InvocationMatcher invocationMatcher) {
        unstubbedInvocations.add(invocationMatcher);
    }

    public List<Invocation> pullStubbedInvocations() {
        List<Invocation> ret = new LinkedList<Invocation>(stubbedInvocations);
        stubbedInvocations.clear();
        return ret;
    }

    public List<InvocationMatcher> pullUnstubbedInvocations() {
        List<InvocationMatcher> ret = new LinkedList<InvocationMatcher>(unstubbedInvocations);
        unstubbedInvocations.clear();
        return ret;
    }

    public void collectData() {
        // TODO Auto-generated method stub
    }

    public void clearData() {
        // TODO Auto-generated method stub
    }

    public void printWarnings(MockitoLogger logger) {
        WarningsPrinter warningsPrinter = new WarningsPrinter(stubbedInvocations, unstubbedInvocations);
        warningsPrinter.print(logger);
    }
}