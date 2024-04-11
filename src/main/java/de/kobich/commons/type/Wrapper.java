package de.kobich.commons.type;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * A container object which may or may not contain a non-{@code null} value. Similar to {@link Optional} but allows to set values. 
 * <p>This wrapper is particularly useful for streams when values outside the scope need to be accessed.
 * <p>Example with compiler error: Local variable selection defined in an enclosing scope must be final or effectively final:<br/>
 * <code>
 * Selection selection = ;
 * list.stream().filter(e -> e.equals(selection))...
 * </code>
 * <p>
 * Example with wrapper:<br/>
 * <code>
 * Wrapper<Selection> selection = ;
 * list.stream().filter(e -> e.equals(selection.get()))...
 * </code>
 * @param <T>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Wrapper<T> {
	private T value;

	public static <T> Wrapper<T> empty() {
		return new Wrapper<T>(null);
	}
	
	public static <T> Wrapper<T> of(T value) {
		return new Wrapper<T>(Objects.requireNonNull(value));
	}

	public void set(@Nullable T value) {
		this.value = value;
	}

	public void setIfNotNull(T value) {
		if (value != null) {
			this.value = value;
		}
	}

	public T get() {
		if (value == null) {
			throw new NoSuchElementException("No value present");
		}
		return value;
	}

	public boolean isPresent() {
		return value != null;
	}

	public boolean isEmpty() {
		return value == null;
	}

	public void ifPresent(Consumer<? super T> action) {
		if (value != null) {
			action.accept(value);
		}
	}

	public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
		if (value != null) {
			action.accept(value);
		}
		else {
			emptyAction.run();
		}
	}
	
	public Optional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return Optional.ofNullable(value);
        } else {
            return predicate.test(value) ? Optional.ofNullable(value) : Optional.empty();
        }
    }

	public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		if (!isPresent()) {
			return Optional.empty();
		}
		else {
			return Optional.ofNullable(mapper.apply(value));
		}
	}
	
	public Optional<T> or(Supplier<? extends Optional<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return Optional.ofNullable(value);
        } else {
            @SuppressWarnings("unchecked")
            Optional<T> r = (Optional<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }
	
	public Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(value);
        }
    }

	public T orElse(T other) {
		return value != null ? value : other;
	}
	
	public T orElseGet(Supplier<? extends T> supplier) {
        return value != null ? value : supplier.get();
    }
	
	public T orElseThrow() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }
	
	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }
}
