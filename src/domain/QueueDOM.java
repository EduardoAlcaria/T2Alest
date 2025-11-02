package domain;

public class QueueDOM<E> {


    private class QueueNode<T> {
        public T element;
        public QueueNode<T> next;

        public QueueNode(T e) {
            element = e;
            next = null;
        }
    }


    private int count;
    private QueueNode<E> head;
    private QueueNode<E> tail;


    public QueueDOM() {
        count = 0;
        head = null;
        tail = null;
    }




    public void enqueue(E element) {
        QueueNode<E> n = new QueueNode<>(element);
        if (count == 0) {
            head = n;
        } else {
            tail.next = n;
        }
        tail = n;
        count++;
    }


    public E dequeue() {
        if (count == 0)
            throw new RuntimeException("Fila vazia!");
        E aux = head.element;
        head = head.next;
        count--;
        if (count == 0) {
            tail = null;
        }
        return aux;
    }


    public E head() {
        if (count == 0)
            throw new RuntimeException("Fila vazia!");
        return head.element;
    }


    public boolean isEmpty() {
        return (count == 0);
    }


    public int size() {
        return count;
    }


    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }
}