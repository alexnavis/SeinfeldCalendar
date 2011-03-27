package com.pongal.seinfeld.db;

import java.util.Set;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.pongal.seinfeld.data.Date;
import com.pongal.seinfeld.data.Task;
import com.pongal.seinfeld.db.DBManager;

public class DBManagerTest extends AndroidTestCase {

    DBManager manager;
    long tasksCount;
    long task1StatusCount;

    @Override
    protected void setUp() throws Exception {
   super.setUp();
   manager = new DBManager(getContext());
   initialize();
    }

    @Override
    protected void tearDown() throws Exception {
   super.tearDown();
   clearData();
    }

    public void initialize() {
   clearData();
   tasksCount = populateTaskTable();
   populateStatusTable();
   populateNotesTable();
   task1StatusCount = 14;
    }

    public void testGetAllTasks() {
   Set<Task> tasks = manager.getTasks();
   assertEquals(tasksCount, tasks.size());
    }

    public void testFetchTask() {
   Task t = manager.getTaskDetails(1);
   assertNotNull(t);
   assertEquals(new Integer(1), t.getId());
   assertEquals("T1", t.getText());
   assertEquals(task1StatusCount, t.getAccomplishedDatesCount());
   assertEquals(2, t.getNotes().keySet().size());
    }

    public void testUpdateTaskCalendar() {
   Task t = manager.getTaskDetails(1);
   Date dt = new Date(2016, 1, 1);
   manager.updateTaskCalendar(t.getId(), dt, true);
   Task newT = manager.getTaskDetails(1);
   assertEquals(t.getAccomplishedDatesCount() + 1, newT.getAccomplishedDatesCount());

   manager.updateTaskCalendar(t.getId(), dt, false);
   Task delT = manager.getTaskDetails(1);
   assertEquals(t.getAccomplishedDatesCount(), delT.getAccomplishedDatesCount());
    }

    public void testUpdateNote() {
   Date dt = new Date(2011, 3, 1);
   String notes = "Upated notes";
   manager.updateNotes(1, dt, notes);
   Task t = manager.getTaskDetails(1);
   assertEquals(notes, t.getNotes().get(dt));
    }

    public void testInsertNote() {
   Date dt = new Date(2011, 5, 1);
   String notes = "New note";
   manager.updateNotes(1, dt, notes);
   Task t = manager.getTaskDetails(1);
   assertEquals(notes, t.getNotes().get(dt));
    }

    public void testDeleteCalendarEntryAlreadyPresent() {
   Task task = manager.getTaskDetails(1);
   int initialSize = task.getAccomplishedDatesCount();
   manager.updateTaskCalendar(task.getId(), new Date(2011, 3, 1), false);
   Task task1 = manager.getTaskDetails(1);
   assertEquals(initialSize - 1, task1.getAccomplishedDatesCount());
    }

    public void testDeleteTask() {
   Task task = manager.getTaskDetails(1);
   manager.deleteTask(task);
   Cursor csr = manager.database.rawQuery("select count(*) from status where id=1", new String[] {});
   int count = 10;
   while (csr.moveToNext()) {
       count = csr.getInt(0);
   }
   assertEquals(0, count);
    }

    public void testDeleteTaskAfterAddingDates() {
   manager.createTask("Some Task");
   Task[] t1 = (Task[]) manager.getTasks().toArray(new Task[0]);
   Task task = t1[t1.length - 1];
   task.addAccomplishedDates(new Date(2012, 1, 1));
   manager.updateTask(task);
   manager.deleteTask(task);
   assertNull(manager.getTaskDetails(task.getId()));
    }

    private int populateTaskTable() {
   String[] insertTaskQueries = new String[] { "Insert into task values (1, 'T1')",
      "Insert into task values (2, 'T2')", "Insert into task values (3, 'T3')" };
   for (String task : insertTaskQueries) {
       manager.database.execSQL(task);
   }
   return insertTaskQueries.length;
    }

    private int populateStatusTable() {
   String[] setUpQueries = new String[] { "INSERT INTO STATUS  (date, task_id) VALUES('03/01/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/02/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/03/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/04/2011', 2);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/05/2011', 2);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/06/2011', 2);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/07/2011', 3);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/08/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/09/2011', 2);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/10/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/11/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/12/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/13/2011', 3);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/14/2011', 3);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/15/2011', 3);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/16/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/17/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/18/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/19/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/20/2011', 2);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/21/2011', 2);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/22/2011', 2);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/23/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/24/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/25/2011', 1);",
      "INSERT INTO STATUS  (date, task_id) VALUES('03/26/2011', 3);" };
   for (String status : setUpQueries) {
       manager.database.execSQL(status);
   }

   return setUpQueries.length;
    }

    private void populateNotesTable() {
   String[] notesQueries = new String[] {
      "INSERT INTO NOTES  (task_id, date, notes) VALUES(1, '03/01/2011', 'Note1 for first task');",
      "INSERT INTO NOTES  (task_id, date, notes) VALUES(1, '02/01/2011', 'Note2 for first task');",
      "INSERT INTO NOTES  (task_id, date, notes) VALUES(2, '02/01/2011', 'Note1 for second task');" };
   for (String status : notesQueries) {
       manager.database.execSQL(status);
   }
    }

    private void clearData() {
   manager.database.execSQL("delete from Status");
   manager.database.execSQL("delete from Task");
   manager.database.execSQL("delete from Notes");
    }
}