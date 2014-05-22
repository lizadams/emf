package gov.epa.emissions.framework.client.tempalloc.editor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import gov.epa.emissions.framework.client.EmfSession;
import gov.epa.emissions.framework.services.EmfException;
import gov.epa.emissions.framework.services.tempalloc.TemporalAllocation;
import gov.epa.emissions.framework.services.tempalloc.TemporalAllocationService;

public class TemporalAllocationPresenter {

    private EmfSession session;
    
    private TemporalAllocationView view;
    
    private TemporalAllocation temporalAllocation;
    
    private List<TemporalAllocationTabPresenter> tabPresenters;
    
    public final String SUMMARY_TAB = "Summary";
    public final String INVENTORIES_TAB = "Inventories";
    public final String TIMEPERIOD_TAB = "Time Period";
    public final String PROFILES_TAB = "Profiles";
    public final String OUTPUT_TAB = "Output";
    
    public TemporalAllocationPresenter(TemporalAllocation temporalAllocation, EmfSession session, TemporalAllocationView view) {
        this.temporalAllocation = temporalAllocation;
        this.session = session;
        this.view = view;
        this.tabPresenters = new ArrayList<TemporalAllocationTabPresenter>();
    }
    
    public void doDisplay() throws EmfException {
        view.observe(this);
        
        temporalAllocation = service().obtainLocked(session.user(), temporalAllocation.getId());
        
        if (!temporalAllocation.isLocked(session.user())) {
            view.notifyLockFailure(temporalAllocation);
            return;
        }
        
        view.display(temporalAllocation);
    }
    
    public void doDisplayNew() {
        view.observe(this);
        view.display(temporalAllocation);
    }

    public void doClose() throws EmfException {
        if (temporalAllocation.getId() != 0)
            service().releaseLocked(session.user(), temporalAllocation.getId());
        closeView();
    }

    private void closeView() {
        view.disposeView();
    }

    public void doSave() throws EmfException {
        saveTabs();
        temporalAllocation.setLastModifiedDate(new Date());
        if (temporalAllocation.getId() == 0) {
            int id = service().addTemporalAllocation(temporalAllocation);
            temporalAllocation = service().obtainLocked(session.user(), id);
        } else {
            temporalAllocation = service().updateTemporalAllocationWithLock(temporalAllocation);
        }
    }

    protected void saveTabs() throws EmfException {
        for (Iterator<TemporalAllocationTabPresenter> iter = tabPresenters.iterator(); iter.hasNext();) {
            TemporalAllocationTabPresenter element = iter.next();
            element.doSave();
        }
    }
    
    private TemporalAllocationService service() {
        return session.temporalAllocationService();
    }
    
    public void set(String tabName, TemporalAllocationTabView view) throws EmfException {
        TemporalAllocationTabPresenter presenter = null;
        if (SUMMARY_TAB.equals(tabName)) {
            presenter = new TemporalAllocationSummaryTabPresenter(view);
        } else if (INVENTORIES_TAB.equals(tabName)) {
            presenter = new TemporalAllocationInventoriesTabPresenter(view);
        } else if (TIMEPERIOD_TAB.equals(tabName)) {
            presenter = new TemporalAllocationTimePeriodTabPresenter(view);
        } else if (PROFILES_TAB.equals(tabName)) {
            presenter = new TemporalAllocationProfilesTabPresenter(view);
        } else if (OUTPUT_TAB.equals(tabName)) {
            presenter = new TemporalAllocationOutputTabPresenter(view);
        }
        if (presenter != null) {
            presenter.doDisplay();
            tabPresenters.add(presenter);
        }
    }
}
